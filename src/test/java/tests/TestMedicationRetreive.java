package tests;

import it.uniroma2.progettoispw.model.dao.DaoException;
import it.uniroma2.progettoispw.model.dao.DaoFactory;
import it.uniroma2.progettoispw.model.domain.ActiveIngredient;
import it.uniroma2.progettoispw.model.domain.MedicinalProduct;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class TestMedicationRetreive {
    int flag = 0;

    void retreiveActiveIngredient(String id){
        ActiveIngredient activeIngredient = null;
        try{
            activeIngredient= DaoFactory.getIstance().getMedicationDao().getAIByID(id);
        } catch (DaoException e) {
            System.out.println("asgas");
            flag = 1;
            return;
        }

        if (activeIngredient == null){
            System.out.println("no");
            flag = 1;
            return;
        }

        assertEquals("PARACETAMOLO", activeIngredient.getName());

    }

    void retreiveMedicinal(String id){
        MedicinalProduct medicinalProduct = null;
        try {
            medicinalProduct = DaoFactory.getIstance().getMedicationDao().getMedicinalProductByID(id);
        } catch (DaoException e) {
            System.out.println("asgas");
            flag = 1;
            return;
        }

        if (medicinalProduct == null){
            System.out.println("no");
            flag = 1;
            return;
        }

        if (!Objects.equals(medicinalProduct.getName(), "TACHIPIRINA")){
            System.out.println(medicinalProduct.getName());
            flag = 1;
        }


    }

    @Test
    public void testMedicationRetreive() {
        String idAI= "N02BE01";
        String idMP = "12745016";
        retreiveActiveIngredient(idAI);
        assertEquals(0, flag);
        retreiveMedicinal(idMP);
        assertEquals(0, flag);
    }
}
