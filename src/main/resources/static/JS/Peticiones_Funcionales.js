

const { createApp } = Vue

Vue.createApp({
  data() {
      return {
          saludar: "hola",
          info: [],
          cuentas: [],
          prueba: "",
          email: "",
          edad: "",
          nombre:""

      };
  },
  created() {
    this.obtenerJSON()
    
  },
  mounted() {},
  methods: {
    obtenerJSON(){
    axios.get("/api/clients").then(response => {this.info=response.data
      this.info.forEach(e => {
        console.log(e)
        console.log(e.accounts)
        
      });
     

    })
    },
  
    agregarJSON(){
      let cliente= {
        name: this.nombre,
        email: this.email,
        age: this.edad
      }
      axios.post(`/rest/clients`,cliente).then(response => {this.obtenerJSON( )})
            
      
    },
    
    eliminarJSON(eliminar){
      
      axios.delete(eliminar).then(response => {this.obtenerJSON()})

    },
    editJason(enlace){
      let cliente={
        name: prompt("nombre nuevo"),
        email: prompt("email nuevo"),
        age: prompt("edad nueva")

      }
      axios.put(enlace,cliente)
    }

     
  },
    computed: {
        
    },
}).mount(`#App`);