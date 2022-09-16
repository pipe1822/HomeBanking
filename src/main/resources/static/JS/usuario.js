
const { createApp } = Vue

Vue.createApp({
  data() {
      return {
          saludar: "hola",
          inicio:"cliente",
          usuario:"",
          usuarioInciado:0,
          prestamos:0,
          info:[],
          cuentasUsuario:[],
          cuentaSel:{},
          cuentaSeladmin:{},
          prueba: "",
          email: "",
          edad: "",
          nombre:"",
          type:"credito",
          cuentamo:0,
          usuarioElegidoAdmin:"",
          cardType:"DEBITO",
          cardColor:"GOLD",
          cuentasPropias:"Propias",
          cuentaORG:"",
          cuentaDSTNO:"",
          amount:0,
          transferDescription:""
      };
  },
  created() {

    axios.get(`/api/clients/current`).then(response =>console.log(response.data.id))

    if (location.pathname.includes('AdministradorInicio')) {
    axios.get("/api/clients").then(response => {this.info=response.data}).then(response=>{
        
    })
    }
   
   if (location.pathname.includes('Usuario_Inicio')||location.pathname.includes('create-cards')||location.pathname.includes('transfers')){
    this.usuarioConSesion()
    console.log("hola pipe, si funciona");
   }
   if (location.pathname.includes(`transaction`)) {
    
    this.accountSeleccionada()
    
   }
   if (location.pathname.includes('account.html')) {
    this.accountSeleccionadaAdmin()
    
   }

   
      
  },
  mounted() {},
  methods: {
    transactions(){
        let accountNumberORG=this.cuentaORG;
        let accountNumberDSTNO=this.cuentaDSTNO;
        let ammount = this.amount;
        let description= this.transferDescription;
        axios.post('api/clients/current/accounts/transactions',`accountNumberORG=${accountNumberORG}&accountNumberDSTNO=${accountNumberDSTNO}&ammount=${ammount}&description=${description}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response=>{
            alert("transferencia exitosa")
        }).catch(response=>{
            alert("transferencia no realizada")
        })

    },
    
   usuarioConSesion(){ 
    
    axios.get(`/api/clients/current`).then(response => {this.info=response.data}).then(response =>{  

        this.prestamos=this.info.loans.length; 
        this.usuarioInciado=this.info ; 
        console.log(this.info)
        console.log(this.prestamos);                       
       
    })  
    },
    agregarCuenta(){
        axios.post('/api/clients/current/accounts',"cardColor=SILVER",{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => console.log('CREATED')).then(response=>this.usuarioConSesion())
    },
    cerrarSesion(){
        localStorage.clear()
    },
    agregarCard(){
        let cardType=this.cardType
        let cardColor=this.cardColor
  
        axios.post('/api/clients/current/cards',`cardType=${cardType}&cardColor=${cardColor}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => alert("Tarjeta Creada")).then(response=>this.usuarioConSesion()).catch(response=>console.log("No Creada"))
    },

   
    idAccountLocal(id){
       
                localStorage.setItem(`idCuenta`,id)
    },

    accountSeleccionada(){
        idAccount=localStorage.getItem(`idCuenta`)
        console.log(idAccount)
        axios.get(`/api/accounts/${idAccount}`).then(response => {this.cuentaSel=response.data}).then(response =>{  
            console.log(this.cuentaSel)
            
        })
           
    },

    accountSeleccionadaAdmin(){
        
            let x= location.search.split("?id=").join("")
            console.log(x)

            axios.get(`/api/accounts/${x}`).then(response => {this.cuentaSeladmin=response.data}).then(response =>{  
                console.log(this.cuentaSeladmin)
                
            })
              
        
           
    },
    agregarJSON(){
        let cliente= {
          name: this.nombre,
          email: this.email,
          age: this.edad
        }
        axios.post(`/rest/clients`,cliente)
        
        alert(`Cliente agregado correctamente`)
        
      },
    obtenerCuenta(objeto){
        this.cuentamo=objeto
    },
    cerrarSesion(){
        axios.post('/api/logout').then(response => console.log('signed out!!!'))
      }
  },
    computed: {
        
    },
}).mount(`#App`);