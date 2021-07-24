package ucf.assignments;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TableOperationsTest {
    TableOperations test = new TableOperations();
    // declare a test ArrayList
    ArrayList<Item> expected = new ArrayList<>();
    ArrayList<Item> actual = new ArrayList<>();


    private void setTestArrayList() {
        Item testArrayItem = new Item("700.99", "1234569870", "Laptop");
        expected.add(testArrayItem);
    }

    @Test
    void addToTable() {

        // set expected test list
        setTestArrayList();

        // call the addToTable method with test params
        test.addToTable("700.99", "1234569870", "Laptop", actual);

        // assert that the two arrays are equal
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void limitText() {

        // set test expected string that is 256 characters
        String expected = "abcgxprDMo8kNNry91IqwiSGqK9XaypHlGWoHeMZwbB55NOwMHSYW1PTfSrj16sg7uR1t8suCHGFHr1PBfASH6Thh7WUJVAevDkt0PLj8eSaoZFftQPEaMoRKRcckIkGMts0LlbVg6I2HFK4B8K2aZqakp2tjARlwfVaP8hDnInmaQ51MN5ZF47CU6pT7MMjPa4CqCw8si0rFtWQ9odGLKmHKGORUDhvQgyYEJiK47eh4JQufpczvtwfhkGrsWth";

        // set actual string = to limitText (expected + more characters)
        String actual = test.limitText("abcgxprDMo8kNNry91IqwiSGqK9XaypHlGWoHeMZwbB55NOwMHSYW1PTfSrj16sg7uR1t8suCHGFHr1PBfASH6Thh7WUJVAevDkt0PLj8eSaoZFftQPEaMoRKRcckIkGMts0LlbVg6I2HFK4B8K2aZqakp2tjARlwfVaP8hDnInmaQ51MN5ZF47CU6pT7MMjPa4CqCw8si0rFtWQ9odGLKmHKGORUDhvQgyYEJiK47eh4JQufpczvtwfhkGrsWth9CIaqM4yt9FWT4bY5mqxmoL5iIFp2qXxp0TzjMNH970FDGN2lP5CGP7KDXoBCO5tJ4336OYlURcGlyGyjajmp1UI03X4tgnLqT69CtbikPLRggbnV1WbWnAZfqh2IeBakaEaPBZZUY0CmGIzowUZ9PEmrluQe7enFyLka48IPzol8HQBwAhJVzO0Mx5dWaHNBWEOZirfU1k4bG81DrcT4eE4uU7DRBIHnobUUeGfYRESjkDc6GnObv5pIQFYYfl7");

        // assertEquals (expected, actual)
        assertEquals(expected, actual);
    }

    @Test
    void removeItem() {
        // set test list
        setTestArrayList();

        // remove item from test list
        expected.remove(0);

        // declare item to add to our actual list
        Item actualItem = new Item("7.00", "7854961247", "Fan");

        // add the item to our
        actual.add(actualItem);

        //call the removeLabel method with the test params
        test.removeItem(0, actual);

        // assert that the two arrays are null
        assertEquals(expected, actual);
    }
}