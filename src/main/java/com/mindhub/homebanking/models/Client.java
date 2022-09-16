package com.mindhub.homebanking.models;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import javax.persistence.OneToMany;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native" )
    @GenericGenerator(name="native", strategy = "native")
    private Long id;
    private  String name;
    private String email;
    private int age;
    private  String password;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER) //mapea dentro de la propiedad cliente todas las cuentas que tengo
    private Set <Account> accounts= new HashSet<>(); // Set <Account> me da la lista de mis cuentas y me verifica que no esten repetidas y me da el nombre de la variable y hashset es lo mismo que un array vacio

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<ClientLoan> clientLoans= new HashSet<>();

    @OneToMany(mappedBy="client", fetch=FetchType.EAGER)
    private Set<Card> cards = new HashSet<>();

    public Client() {
    }

    public Client(String name, String email, Integer age, String password) {
        this.name = name;
        this.email = email;
        this.age= age;
        this.password=password;
    }
    public Set<Account> getAccounts() {
        return accounts;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<ClientLoan> getClientLoans() {
        return clientLoans;
    }

    public void setClientLoans(Set<ClientLoan> clientLoans) {
        this.clientLoans = clientLoans;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addAccounts(Account account){
        account.setClient(this);
        accounts.add(account);

    }

}
