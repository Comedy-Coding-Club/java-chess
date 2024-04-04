package service;

import repository.ChessBoardRepositoryImpl;
import repository.ChessGameRepositoryImpl;
import repository.dao.ChessBoardDao;
import repository.dao.ChessGameDao;

public class ServiceFactory {
    private static final ServiceFactory serviceFactory = new ServiceFactory();
    private static final ChessGameService chessGameService;
    static {
        ChessBoardRepositoryImpl chessBoardRepository = new ChessBoardRepositoryImpl(new ChessBoardDao());
        ChessGameRepositoryImpl chessGameRepository = new ChessGameRepositoryImpl(new ChessGameDao());
        chessGameService = new ChessGameService(chessBoardRepository, chessGameRepository);
    }

    private ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return serviceFactory;
    }

    public ChessGameService getChessGameService() {
        return chessGameService;
    }
}
