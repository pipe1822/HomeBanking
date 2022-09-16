

const { createApp } = Vue

Vue.createApp({
   
  data() {
      return {
        saludo:"hola",
        autenticado:[],
        cardType:"DEBITO",
        cardColor:"GOLD",
        tarjetas:0,
        tarjetasActivas:[],
        numeroTarjeta:"",
        fecha: new Date(),
        fechaActual:""
        
      
    
    };
},
created() {
    this.usuarioConSesion();
    this.tarjetaVencida();
    
},
mounted(){},
methods:{
    tarjetaVencida(){
        ano=this.fecha.getFullYear()
        meses=this.fecha.getMonth()+1
        dias=this.fecha.getDate()
        this.fechaActual=`${ano}-${meses}-${dias}`
        console.log(this.fechaActual)
    },
    usuarioConSesion(){ 
        axios.get(`/api/clients/current`).then(response => {this.autenticado=response.data}).then(response=>{
            
            this.tarjetasActivas=this.autenticado.card.filter(card=> card.cardStatus=="ACTIVE")

            this.tarjetas=this.tarjetasActivas.length
        })
    },
    agregarCard(){
        let cardType=this.cardType
        let cardColor=this.cardColor
  
        axios.post('/api/clients/current/cards',`cardType=${cardType}&cardColor=${cardColor}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => alert("Tarjeta Creada")).then(response=>this.usuarioConSesion()).catch(response=>alert("Tienes Una Igual Pelotudo"))
    },
    bloquear(card){

        this.numeroTarjeta=card.number
        if (card.cardType=="DEBITO") {
            return alert("no puedes bloquear tu tarjeta debito, debes bloquear la cuenta con la que es dueña la tarjeta")            
        }
        axios.patch(`http://localhost:8080/api/cards/status/cancel`,`cardNumber=${this.numeroTarjeta}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(alert(`tu tarjeta fue bloqueada`)).then(response=>this.usuarioConSesion())
    }


},
computed: {
        
},
}).mount(`#App`);