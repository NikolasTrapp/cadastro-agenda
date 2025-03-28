package br.com.trapp.cadastroagendabackend.component;

import br.com.trapp.cadastroagendabackend.exception.TransactionException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

@Component
public class AbrirTransacaoImpl implements AbrirTransacao {

    @Override
    @Transactional
    public <T> T callInTransaction(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    @Override
    @Transactional
    public void runInTransaction(Supplier<?> suplier) {
        try {
            suplier.get();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public <T> T callInReadOnlyTransaction(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void runInReadOnlyTransaction(Supplier<?> suplier) {
        try {
            suplier.get();
        } catch (Exception e) {
            throw new TransactionException(e);
        }
    }
}
