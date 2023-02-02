package com.shulha.repository;

import com.shulha.config.HibernateFactoryUtil;
import com.shulha.model.Engine;
import com.shulha.model.Order;
import com.shulha.model.PassengerCar;
import com.shulha.model.Truck;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class OrderHibernateRepository {
    public static void main(String[] args) {
        final Order order = new Order();
        final PassengerCar passengerCar = new PassengerCar();
        final Truck truck = new Truck();
        final Engine engine = passengerCar.getEngine();
        final Engine engine1 = truck.getEngine();
        order.getCars().add(passengerCar);
        order.getCars().add(truck);

        final SessionFactory sessionFactory = HibernateFactoryUtil.getSessionFactory();
        final Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.save(engine);
        session.save(engine1);
        session.save(passengerCar);
        session.save(truck);
        session.save(order);
        session.getTransaction().commit();
        session.close();
    }
}
