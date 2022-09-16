const { createApp } = Vue

Vue.createApp({
   
  data() {
      return {
        saludo:"hola",
        monedas:"",
        autenticado:[],
        prestamos:"",
        principalCard:{},
        principalAccount:[],
        inicio:0,
        cantidadCuentas:0,
        cuentaTransacciones:[],
        prestamoSel:[],
        colorNewCard:"TITANIUM",
        accountType:""

        
      
    
    };
},
created() {
    fetch("https://api.coindesk.com/v1/bpi/currentprice.json").then(response=>response.json()).then(data=>{     
    this.monedas=Object.entries(data.bpi)
    console.log(this.monedas)
    })
    axios.get(`/api/clients/current`).then(response =>console.log(response.data.id))
    this.usuarioConSesion();
    
},
mounted(){},
methods:{
    usuarioConSesion(){ 
    let ordenAccounts = []

        axios.get(`/api/clients/current`).then(response => {this.autenticado=response.data}).then(response =>{  
    
            this.prestamos=this.autenticado.loans.length;  
            console.log(this.autenticado);
            console.log(this.prestamos); 
            ordenAccounts=this.autenticado.accounts
            ordenAccounts.sort((a,b)=>{
                return b.balance - a.balance
            })
            this.principalAccount=ordenAccounts[0]
            this.principalCard=this.principalAccount.card
            this.cantidadCuentas=this.autenticado.accounts.length
                               
        })  },
    perfil(){
        this.inicio=0
    },
    accounts(objeto){
        this.inicio=1
        this.cuentaTransacciones=objeto
    },
    showPrestamos(objeto){
        this.inicio=2
        this.prestamoSel=objeto
    },
    agregarCuenta(){
        axios.post('/api/clients/current/accounts',`cardColor=${this.colorNewCard}&accountType=DEBIT`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => alert(`Obtuviste una cuenta Debito, y el color de tu tarjeta es ${this.colorNewCard}`)).then(response=>this.usuarioConSesion())
    },

    aparecerFormularioRegistro() {
        var loginFormContainer = document.querySelector(".login-form-container");
        var registerForm = document.querySelector(".contenedor-titulo-registro-form");
        var loginForm = document.querySelector(".contenedor-titulo-iniciarSesion-form");

        loginFormContainer.style.display = "block";
        loginForm.style.display = "none";
        registerForm.style.display = "block";

    },
    aparecerFormLogin() {
        var registerForm = document.querySelector(".contenedor-titulo-registro-form");
        var loginForm = document.querySelector(".contenedor-titulo-iniciarSesion-form");

        registerForm.style.display = "none";
        loginForm.style.display = "block";
    },

    ocultarForm() {
        var loginFormContainer = document.querySelector(".login-form-container");
        loginFormContainer.style.display = "none";
    },
    cerrarSesion(){
        axios.post('/api/logout').then(response => console.log('signed out!!!'))
      }

},
computed: {
        
},
}).mount(`#App`);


