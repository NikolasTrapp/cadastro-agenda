package br.com.trapp.cadastroagendabackend.component;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public interface AbrirTransacao {

    <T> T callInTransaction(Callable<T> callable);
    void runInTransaction(Supplier<?> suplier);

    <T> T callInReadOnlyTransaction(Callable<T> callable);
    void runInReadOnlyTransaction(Supplier<?> suplier);


}
