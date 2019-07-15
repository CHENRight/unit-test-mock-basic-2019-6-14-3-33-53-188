package cashregister;


import org.junit.jupiter.api.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class CashRegisterTest {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream outStream = System.out;

    @BeforeEach
    void setUpStream(){
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    void restoreStreams(){
        System.setOut(outStream);
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Item item = new Item("Coco",1.0);
        Item[] items = new Item[]{item};
        Purchase purchase = new Purchase(items);
        Printer printer = new Printer();
        CashRegister cashRegister = new CashRegister(printer);
        cashRegister.process(purchase);
        Assertions.assertEquals("Coco\t1.0\n",outContent.toString());
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {

        Purchase mockPurchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(new Printer());

        when(mockPurchase.asString()).thenReturn("purchase mock");
        cashRegister.process(mockPurchase);

        assertThat(outputStream.toString()).isEqualTo("purchase mock");
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        Printer mockPrinter = mock(Printer.class);
        Purchase mockPurchase = mock(Purchase.class);
        CashRegister cashRegister = new CashRegister(mockPrinter);

        when(mockPurchase.asString()).thenReturn("purchase mock");
        cashRegister.process(mockPurchase);
        verify(mockPurchase).asString();

    }

}
