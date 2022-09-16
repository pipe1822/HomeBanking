
const { createApp } = Vue


Vue.createApp({
  data() {
      return {
          saludar: "hola",
          inicio:"cliente",
          usuario:"",
          usuarioInciado:false,
          info:[],
          cuentasUsuario:[],
          prueba: "",
          email: "",
          edad: "",
          nombre:"",
          password:"",
          contador:0,
        

      };
  },
  created() {

    
 
    
  },
  mounted() {},
  methods: {
    agregarClase(){
      const signUp=document.querySelector(`.sign-up`)
      const signIn=document.querySelector(`.sign-in`) 
      signIn.classList.toggle(`active`)
      signUp.classList.toggle(`active`)
      this.contador=this.contador+1
    },
    registrarNuevoUsuario(){
      let email=this.email
      let password=this.password
      let name=this.nombre

      axios.post('/api/clients',`name=${name}&age=55&email=${email}&password=${password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => console.log('registered')).catch(response=>console.log(response.response.data));

    },

    obtenerIdRuta(){
      let x= location.search.split("?id=").join("")
      console.log(x)
      

    },
    InicioSesion(){
      let email=this.email
      let password=this.password

      axios.post('/api/login',`email=${email}&password=${password}`,{headers:{'content-type':'application/x-www-form-urlencoded'}}).then(response => location.href="Perfil-principal.html").catch(response=>{
        console.log("No tenemos iniciado ningun usuario")
        swal({
          title: "Usuario o contraseña incorrecta",
          icon: "error",
        });
      })},
      
      cerrarSesion(){
        axios.post('/api/logout').then(response => console.log('signed out!!!'))
      }
  },
    computed: {
       sesionAbierta(){
        axios.get(`/api/clients/current`).then(response =>{console.log(response.data.id)
          
          if (response.data.id!=0) {
            this.usuarioInciado=true
            
          }

        })
       }
        
    },
}).mount(`#App`);