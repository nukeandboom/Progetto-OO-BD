public class Utente {
    protected int id;
    private boolean ConnectedAsUser;
    public Autore child;
    public Utente(int id)
    {
        this.id = id;
        ConnectedAsUser = false;
    }
    public Autore SignIn_As_Author(String username, String password)
    {
        try
        {
            if(!ConnectedAsUser)
                throw new Exception("> [ERRORE]: E' necessario connettersi come utente!");
        }
        catch (Exception exception)
        {
            System.out.println(exception.getMessage());
            return  null;
        }

       try
       {
           if(username.contentEquals("") || password.contentEquals(""))
               throw new Exception("> [ERRORE]: le credenziali non possono essere vuote!");

       }catch (Exception exception)
       {
           System.out.println(exception.getMessage());
           return  null;
       }

        System.out.println("> Registrazione ultimata con successo");
        return  new Autore(username,password,id);
    }

    public void LogIn()
    {
        ConnectedAsUser = !ConnectedAsUser;
        System.out.println("> Accesso effettuato : <Utente>");
    }
    public void LogOff()
    {
        ConnectedAsUser = !ConnectedAsUser;

        if(child.ConnectedAsAuthor)
            child.LogOff();

        System.out.println("> Utente disconnesso!");
    }
}
