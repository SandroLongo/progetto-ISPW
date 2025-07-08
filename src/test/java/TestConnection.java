import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.dbfiledao.ConnectionFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class TestConnection {

    @Test
    public void testConnection() {
        int flag = 0;

        try{
            Connection con = ConnectionFactory.getConnection();
        } catch (DaoException e){
            flag = 1;
        }

        assertEquals(0,flag );
    }
}
