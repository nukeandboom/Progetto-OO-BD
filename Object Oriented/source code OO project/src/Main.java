
public class Main {
    public static void main(String[] args)
    {
        Utente user1 = new Utente(0);
        Utente user2 = new Utente(0);

        user1.LogIn();
        user2.LogIn();

        user1.child = user1.SignIn_As_Author("lello","1234");
        user2.child = user2.SignIn_As_Author("le","1234");
        user1.child.Login("lello","1234");
        user2.child.Login("le","1234");

        //implementa la funzione di rifiuto di tutte le proposte
        // fai in modo che non possano esistere due Autori con lo stesso username
        // controlla che le funzioni inerenti all'autore non siano accessibili anche quando è disconnesso
        // prova ad implementare una classe parametrica che verifichi se un elemento in input è null o non esistente

        Pagina pag1 = user1.child.CreaPagina("Wiki");


        Paragrafo par1 = new Paragrafo("Ciao",pag1);
        Paragrafo par2 = new Paragrafo("hey",pag1);

        user2.child.InviaProposta(par1,user1.child);
        user2.child.InviaProposta(par2,user1.child);

        user1.child.MostraPagineSoggetteAProposta();
        Pagina pag2 = user1.child.OttieniPagina("Wiki");
        user1.child.MostraProposteInerentiAdUnaPagina(pag2);
        Paragrafo par3 = user1.child.OttieniParagrafo(0,pag2);
        user1.child.ElaborazioneProposta(par3,pag2);

        /*
        Paragrafo par5 = new Paragrafo(0,pag1);

        user2.child.InviaProposta(par5,user1.child);

        user1.child.MostraPagineSoggetteAProposta();
         pag2 = user1.child.OttieniPagina("Wiki");
        user1.child.MostraProposteInerentiAdUnaPagina(pag2);
         par3 = user1.child.OttieniParagrafo(0,pag2);
        user1.child.ElaborazioneProposta(par3,pag2);
        */

        pag2.MostraTesto();

    }
}