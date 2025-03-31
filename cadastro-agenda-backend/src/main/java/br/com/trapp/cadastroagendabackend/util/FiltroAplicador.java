package br.com.trapp.cadastroagendabackend.util;

import br.com.trapp.cadastroagendabackend.enums.Operacao;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.Path;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.lang.reflect.Field;

public class FiltroAplicador {

    public static BooleanExpression aplicarFiltro(List<Filtro> filtros, EntityPathBase<?> qEntidade) {
        BooleanExpression exp = null;

        for (Filtro filtro : filtros) {
            String campo = filtro.getCampo();
            String valor = filtro.getValor();
            Operacao operacao = filtro.getOperacao();

            Path<?> path = getPathParaCampo(campo, qEntidade);

            if (path == null) {
                continue;
            }

            BooleanExpression expressaoFiltro = aplicarOperacao(operacao, path, valor);

            if (exp == null) {
                exp = expressaoFiltro;
            } else {
                exp = exp.and(expressaoFiltro);  // Adiciona o filtro à expressão existente
            }
        }
        return exp;
    }

    private static Path<?> getPathParaCampo(String campo, EntityPathBase<?> qEntidade) {
        try {
            Field field = qEntidade.getClass().getDeclaredField(campo);
            field.setAccessible(true);
            return (Path<?>) field.get(qEntidade);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static BooleanExpression aplicarOperacao(Operacao operacao, Path<?> path, String valor) {
        switch (path) {
            case StringPath strPath -> {
                return switch (operacao) {
                    case IGUAL -> strPath.equalsIgnoreCase(valor);
                    case DIFERENTE -> strPath.ne(valor);
                    case CONTEM -> strPath.containsIgnoreCase(valor);
                    case NAO_CONTEM -> strPath.containsIgnoreCase(valor).not();
                    case VAZIO -> strPath.isEmpty();
                    default -> throw new IllegalArgumentException("Operação não suportada para String");
                };
            }
            case NumberPath numberPath -> {
                var valorNumerico = new BigDecimal(valor);
                return switch (operacao) {
                    case IGUAL -> numberPath.eq(valorNumerico);
                    case DIFERENTE -> numberPath.ne(valorNumerico);
                    case MAIOR -> numberPath.gt(valorNumerico);
                    case MENOR -> numberPath.lt(valorNumerico);
                    case MAIOR_IGUAL -> numberPath.goe(valorNumerico);
                    case MENOR_IGUAL -> numberPath.loe(valorNumerico);
                    default -> throw new IllegalArgumentException("Operação não suportada para Number");
                };
            }
            case BooleanPath booleanPath -> {
                boolean valorBooleano = Boolean.parseBoolean(valor);
                return switch (operacao) {
                    case IGUAL -> booleanPath.eq(valorBooleano);
                    case DIFERENTE -> booleanPath.ne(valorBooleano);
                    default -> throw new IllegalArgumentException("Operação não suportada para Boolean");
                };
            }
            case DateTimePath timePath -> {
                DateTimePath<LocalDateTime> dateTimePath = (DateTimePath<LocalDateTime>) path;
                try {
                    var valorData = LocalDateTime.parse(valor);
                    return switch (operacao) {
                        case IGUAL -> dateTimePath.eq(valorData);
                        case DIFERENTE -> dateTimePath.ne(valorData);
                        case MAIOR -> dateTimePath.gt(valorData);
                        case MENOR -> dateTimePath.lt(valorData);
                        case MAIOR_IGUAL -> dateTimePath.goe(valorData);
                        case MENOR_IGUAL -> dateTimePath.loe(valorData);
                        default -> throw new IllegalArgumentException("Operação não suportada para Date");
                    };
                } catch (Exception e) {
                    throw new IllegalArgumentException("Erro ao parsear data", e);
                }
            }
            case DatePath datePath -> {
                DatePath<java.time.LocalDate> dateTimePath = (DatePath<java.time.LocalDate>) datePath;
                try {
                    var valorData = LocalDate.parse(valor);
                    return switch (operacao) {
                        case IGUAL -> dateTimePath.eq(valorData);
                        case DIFERENTE -> dateTimePath.ne(valorData);
                        case MAIOR -> dateTimePath.gt(valorData);
                        case MENOR -> dateTimePath.lt(valorData);
                        case MAIOR_IGUAL -> dateTimePath.goe(valorData);
                        case MENOR_IGUAL -> dateTimePath.loe(valorData);
                        default -> throw new IllegalArgumentException("Operação não suportada para Date");
                    };
                } catch (Exception e) {
                    throw new IllegalArgumentException("Erro ao parsear data", e);
                }
            }
            case null, default -> throw new IllegalArgumentException("Tipo de campo não suportado para filtro");
        }
    }
}
