import java.util.*;

public class Autore  extends  Utente
{
    protected boolean ConnectedAsAuthor;
    public final String username;
    public final Date dataIscrizione;
    private final String password;
    public ArrayList<Pagina> pagScritte = new ArrayList<Pagina>();

    private Map<Pagina,ArrayList<Paragrafo>> proposteInAttesa = new HashMap<Pagina,ArrayList<Paragrafo>>();


    public Autore(String username, String password,int id)
    {
        super(id);
        this.username = username;
        this.password = password;
        dataIscrizione = new Date();
        ConnectedAsAuthor = false;
    }

    public  void Login(String username, String password)
    {
        boolean check = true;

        try
        {
            if(ConnectedAsAuthor)
                throw new Exception("> [ERRORE]: L'autore è già connesso!");

        }catch (Exception exception) {
            System.out.println(exception.getMessage());
            check  = false;
        }

        try
        {
            if(!this.password.contentEquals(password) || !this.username.contentEquals(username))
                throw new Exception("> [ERRORE]: credenziali errate!");
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            check = false;
        }

        if(check)
        {
            ConnectedAsAuthor = !ConnectedAsAuthor;
            System.out.println("> Accesso effettuato : <Autore>");
        }
    }

    public void LogOff()
    {
        ConnectedAsAuthor = !ConnectedAsAuthor;
        System.out.println("> Autore disconnesso!.");
    }

    public Pagina CreaPagina(String titolo)
    {
        try
        {
            if(titolo == null || titolo.isBlank())
                throw new NullPointerException("> [ERRORE]: il titolo della pagina non può essere vuoto o nullo!");
        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return  null;
        }
        pagScritte.add(new Pagina(titolo,this));

        return  pagScritte.getLast();
    }

    public void InviaProposta(Paragrafo daRevisionare, Autore destinatario)
    {
        try
        {
          if(daRevisionare == null || destinatario == null)
              throw new NullPointerException("> [ERRORE]: Invio proposta fallito -> le informazioni inserite sono inesistenti!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            if(daRevisionare.posizione < 0)
                throw new IndexOutOfBoundsException("> [ERRORE]: Invio proposta fallito -> Indice paragrafo inesistente!");

        }catch (IndexOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            switch (daRevisionare.operazioneDaEffettuare)
            {
                case Add ->
                {
                    if(daRevisionare.posizione > daRevisionare.paginaDiRiferimento.testo.size())
                        throw new Exception("> [ERRORE]:  Invio proposta fallito -> Indice paragrafo inesistente!");
                    break;
                }
                case Remove->
                {
                    if(daRevisionare.posizione >= daRevisionare.paginaDiRiferimento.testo.size())
                        throw new Exception("> [ATTENZIONE]: Invio proposta fallito -> non è possibile eliminare paragrafi da una pagina vuota!");
                    break;
                }
                case Update ->
                {
                    if(daRevisionare.posizione >= daRevisionare.paginaDiRiferimento.testo.size())
                        throw new Exception("> [ATTENZIONE]: Invio proposta fallito -> non è possibile aggiornare paragrafi da una pagina vuota!");
                    break;
                }
            }
        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            if(daRevisionare.operazioneDaEffettuare == Azione.None)
                throw new Exception("> [ATTENZIONE]: la proposta inviata non ha nessuna operazione da effettuare, pertanto verrà scartata.");

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        // nel caso in cui il mittente fosse proprio il proprietario della pagina,
        // allora la modifica verrà apportata senza alcuna valutazione.
        if(destinatario != this)
            destinatario.RiceviNotifica(daRevisionare);
        else
            daRevisionare.paginaDiRiferimento.ApplicaParagrafo(daRevisionare);
    }

    public Pagina OttieniPagina(String titolo)
    {
        try
        {
            if(titolo == null)
                throw new NullPointerException("> [ERRORE]: raccolta info fallita -> il titolo inserito non può essere nullo!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }

        for (var pag : proposteInAttesa.entrySet())
        {
            if(pag.getKey().titolo.contentEquals(titolo))
                return pag.getKey();
        }

        System.out.println("> [ATTENZIONE]: raccolta info fallita -> il titolo inserito non risulta presente nelle notifiche!");
        return  null;
    }

    /** Questa funzione consente di visualizzare le pagine a cui sono riferite le proposte di modifica */
    public void MostraPagineSoggetteAProposta()
    {
        System.out.println("> Sono disponibili modifiche alle seguenti pagine: ");

        for( var pag: proposteInAttesa.entrySet())
        {
            System.out.println(pag.getKey().titolo);
        }
    }

    /** A seguito della revisione delle pagine interessate dalle proposte di modifica,è possibile visualizzare tutte le notifiche della singola pagina di interesse.*/
    public void MostraProposteInerentiAdUnaPagina(Pagina selezionata)
    {
        boolean check = true;

        try
        {
            if(selezionata == null)
                throw new NullPointerException("> [ERRORE]: Elaborazione proposta fallita -> la pagina selezionata non può essere nulla!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            check = false;
        }

        try
        {
            if(!proposteInAttesa.containsKey(selezionata) && selezionata != null)
                throw new Exception("> [ERRORE]: Elaborazione proposta fallita -> la pagina selezionata non risulta nelle notifiche!");

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            check = false;
        }

        if(check)
        {
            System.out.print("> Proposte inerenti alla pagina ");
            System.out.print(selezionata.titolo);
            System.out.println(" : ");

            for(int i = 0; i < proposteInAttesa.get(selezionata).size(); i++)
            {
                System.out.print(i);
                System.out.print(") ");
                System.out.print(proposteInAttesa.get(selezionata).get(i).contenuto);
                System.out.print(" [");
                System.out.print((proposteInAttesa.get(selezionata).get(i).operazioneDaEffettuare));
                System.out.println("]");
            }
        }
    }

    public void ElaborazioneProposta(Paragrafo selezionato, Pagina destinazione)
    {
        try
        {
            if(destinazione == null)
                throw new NullPointerException("> [ERRORE]: Elaborazione proposta fallita -> la pagina inserita non può essere nulla!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            if(!proposteInAttesa.containsKey(destinazione))
                throw new Exception("> [ERRORE]: Elaborazione proposta fallita-> la pagina selezionata non risulta nelle notifiche!");

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            if(selezionato == null)
                throw new NullPointerException("> [ERRORE]: Elaborazione proposta fallita -> il paragrafo selezionato non può essere nullo!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        try
        {
            if(!proposteInAttesa.get(destinazione).contains(selezionato))
                throw new Exception("> [ERRORE]: Elaborazione proposta fallita -> il paragrafo selezionato non risulta nelle notifiche!");

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return;
        }

        var listaParagrafi = proposteInAttesa.get(selezionato.paginaDiRiferimento);
        int index = listaParagrafi.indexOf(selezionato);

        listaParagrafi.get(index).stato= Stato.Approved;
        selezionato.paginaDiRiferimento.ApplicaParagrafo(selezionato);

        for (Paragrafo par : listaParagrafi)
        {
            par.stato = Stato.Refused;
        }

        proposteInAttesa.remove(selezionato.paginaDiRiferimento);
    }

    public  void RiceviNotifica(Paragrafo daNotificare)
    {
        if(proposteInAttesa.containsKey(daNotificare.paginaDiRiferimento))
            proposteInAttesa.get(daNotificare.paginaDiRiferimento).add(daNotificare);
        else
        {
            ArrayList<Paragrafo> temp = new ArrayList<>();
            temp.add(daNotificare);
            proposteInAttesa. put(daNotificare.paginaDiRiferimento, temp);
        }
    }

    public Paragrafo OttieniParagrafo(int index, Pagina pagina)
    {
        try
        {
            if(pagina == null)
                throw new NullPointerException("> [ERRORE]: raccolta info fallita -> la pagina inserita non può essere nullo!");

        }catch (NullPointerException exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }

        try
        {
            if(!proposteInAttesa.containsKey(pagina))
                throw new Exception("> [ERRORE]: raccolta info fallita -> la pagina selezionata non risulta nelle notifiche!");

        }catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return null;
        }

        try
        {
            if(index < 0 || index > proposteInAttesa.get(pagina).size())
                throw new IndexOutOfBoundsException("> [ERRORE]:  raccolta info fallita -> Indice paragrafo inesistente!");
            else
                return  proposteInAttesa.get(pagina).get(index);

        }catch (IndexOutOfBoundsException exception) {
            System.out.println(exception.getMessage());
            return  null;
        }
    }
}
