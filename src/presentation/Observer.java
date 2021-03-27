package presentation;

public class Observer {

    ChefGraphicalUserInterface chefGraphicalUserInterface;

    public Observer(ChefGraphicalUserInterface chefGraphicalUserInterface){
        this.chefGraphicalUserInterface=chefGraphicalUserInterface;
    }

    public void update(String[] args){
        String s="Trebuie sa prepar urmatoarea comanda:\n";
        for(int i=1;i<args.length;i++){
            s=s+args[i]+";";
        }
        s=s+"\n";
        chefGraphicalUserInterface.getText().setText(chefGraphicalUserInterface.getText().getText()+s);
    }

}
