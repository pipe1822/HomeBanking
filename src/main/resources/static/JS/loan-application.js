
const { createApp } = Vue

Vue.createApp({
   
  data() {
      return {
        saludo:"hola",
        monedas:"",
        autenticado:[],
        prestamosUsuario:"",
        principalCard:{},
        inicio:0,
        prestamos:[],
        prestamoSeleccionado:1,
        cuotasParaPagar:[],
        objetoPrestamo:{},
        montoSolicitado:50000,
        cuentaDSTNO:"",
        pagoMensual:0,
        cuotaSeleccionada:0,

      
    
    };
},
created() {
 
    
    this.usuarioConSesion();
    this.traerLoans();
    this.pagosMensuales();
},
mounted(){},
methods:{
    usuarioConSesion(){ 
    
        axios.get(`/api/clients/current`).then(response => {this.autenticado=response.data}).then(response =>{  
    
            this.prestamosUsuario=this.autenticado.loans;
            console.log(this.prestamosUsuario)  
                            
        })  },
    traerLoans(){
        axios.get(`/api/loans`).then(response => this.prestamos=response.data).then(response=>console.log(this.prestamos)).then(response=>{
            let arrayCuotas = [];
                this.prestamos.forEach(prestamo => {
                    if (prestamo.id==this.prestamoSeleccionado) {
                        
                        arrayCuotas.push(prestamo.payments) 
                         
                        this.cuotasParaPagar=arrayCuotas 
                        this.objetoPrestamo=prestamo
                        console.log(arrayCuotas)
                        console.log(this.objetoPrestamo)
                    }
                    
                    
                });

        })
    },

    pagosMensuales(){
        let montoSolicitado=parseInt(this.montoSolicitado)
        let cuotasParaPagar =parseInt(this.cuotaSeleccionada)
        console.log(cuotasParaPagar)
        this.pagoMensual=parseInt((montoSolicitado*1.2))/cuotasParaPagar
        console.log(this.pagoMensual)
    },

    crearPrestamo(){
        let idLoan=this.prestamoSeleccionado;
        let amount=this.montoSolicitado;
        let payments=this.cuotaSeleccionada;
        let numberDSTN=this.cuentaDSTNO;
    axios.post('/api/loan',{"loan":`${idLoan}`,
    "amount":`${amount}`,
    "payments":`${payments}`,
    "numberDSTN":`${numberDSTN}`}).then(response => alert('CREADOOOO'))
    }


},
computed: {
        
},
}).mount(`#App`);