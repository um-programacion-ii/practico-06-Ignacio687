package ar.edu.um.programacion2.curso2024.service;

public class IoCConteinerService implements IoCService{
    public static IoCConteinerService ioCConteiner;

    private IoCConteinerService() {

    }

    public static IoCConteinerService getInstance() {
        if (ioCConteiner == null) {
            ioCConteiner = new IoCConteinerService();
        }
        return ioCConteiner;
    }
}
