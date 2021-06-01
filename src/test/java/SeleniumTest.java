import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //reikalingas, kad galetume rikiuoti, eiles tvarka daryti. Reikia parasyti ranka

public class SeleniumTest {
    @Before //veiksmai, kurie bus atliekami pries kiekviena testa - surandamas webdriveris, uzkraunamas, browser, url ir kt.
    public void setup(){ //Testai visada yra public, nieko negrazins, visi testai turi buti void
        Selenium.setup();  // KlasesPav.metodas(); kreipiames i Selenium.java klases setup() koda, kuri testuosime. static - funkcinis programavimas, todel nereikia kurti naujo objekto, kad galetume panaudoti ta metoda.
    }

    @Test
    @Order(2)
    public void searchByKeywordTest(){
        Selenium.search("Baranauskas");
    }

    @Test
    @Order(1)
    public void getResults(){
        Selenium.search("Baranauskas"); //kad gautume rezultatus, reikia pirma atlikti paieska
        //pirmas parametras - expected, antras - actual
        Assert.assertEquals(189000, Selenium.getResults()); //Assert -biblioteka JUnit
    }

    @After
    public void close(){
        Selenium.close();
    }
}
