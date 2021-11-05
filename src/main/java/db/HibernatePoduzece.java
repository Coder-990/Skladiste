package main.java.db;

import main.java.dto.PoduzeceToDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HibernatePoduzece{

    @Autowired
    private static HibernateDatabase hibarnateDatabase;

    public void create (PoduzeceToDto poduzece) {
            hibarnateDatabase.save(poduzece);
    }

    public List<PoduzeceToDto> get () {
        return hibarnateDatabase.findAll();
    }


}
