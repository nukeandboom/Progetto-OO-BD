import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class Pagina
{
    public String titolo;
    public Date   dataCreazione;
    public Autore proprietario;
    public ArrayList<Paragrafo> testo = new ArrayList<Paragrafo>();

    public Pagina(String titolo, Autore proprietario)
    {
        this.titolo = titolo;
        this.proprietario = proprietario;
        dataCreazione = new Date();
    }

    public void MostraTesto()
    {
        System.out.print("> ");
        System.out.print(this.titolo);
        System.out.print(" , from ");
        System.out.print(this.proprietario.username);
        System.out.println(" : ");

        for (Paragrafo par : testo)
        {
            System.out.println(par.contenuto);
        }
    }

    public  void ApplicaParagrafo(Paragrafo paragrafo)
    {
        switch (paragrafo.operazioneDaEffettuare)
        {
            case Add -> {
                paragrafo.posizione = testo.size() + 1;
                testo.add(paragrafo);
                break;
            }

            // nel caso di Remove, il contenuto della variabile
            // "daAggiungere" è stato preventivamente impostato come "", al fine di imitare
            // la presenza di un paragrafo cancellato sulla pagina.

            case Update, Remove -> {
                testo.set(paragrafo.posizione,paragrafo);
                break;
            }
        }
    }

}
