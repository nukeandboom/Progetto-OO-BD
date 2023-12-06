import java.util.Date;

public class Paragrafo
{
    public String contenuto;
    public Stato stato;
    public Azione operazioneDaEffettuare;
    public Date data_creazione;
    public int posizione;
    public Pagina paginaDiRiferimento;

    /** Questo costruttore viene utilizzato per modificare un paragrafo dalla pagina di destinazione. */
    public  Paragrafo(String contenuto, int posizione, Pagina pagina)
    {
        this.contenuto = contenuto;
        this.posizione = posizione;
        stato = Stato.Pending;
        operazioneDaEffettuare = Azione.Update;
        paginaDiRiferimento = pagina;
        data_creazione = new Date();
    }

    /** Questo costruttore viene utilizzato per aggiungere un paragrafo alla pagina di destinazione. */
    public  Paragrafo(String contenuto, Pagina pagina)
    {
        this.contenuto = contenuto;
        this.posizione = 0;
        stato = Stato.Pending;
        operazioneDaEffettuare = Azione.Add;
        paginaDiRiferimento = pagina;
        data_creazione = new Date();
    }

    /** Questo costruttore viene utilizzato per eliminare un paragrafo dalla pagina di destinazione. */
    public  Paragrafo( int posizione, Pagina pagina)
    {
        this.contenuto = "";
        this.posizione = posizione;
        stato = Stato.Pending;
        operazioneDaEffettuare = Azione.Remove;
        paginaDiRiferimento = pagina;
        data_creazione = new Date();
    }
}
