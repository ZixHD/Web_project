<template>

  <div>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
      <div class="container-fluid">
        <a class="navbar-brand" href="#">RAF Turistički vodič</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
          <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
          <ul class="navbar-nav me-auto mb-2 mb-lg-0">
            <li class="nav-item">
              <router-link to="/" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'HomePage'}">Početna stranica</router-link>
            </li>
            <li class="nav-item">
              <router-link :to="{name: 'MostReadPage'}" tag="a" class="nav-link" :class="{active: this.$router.currentRoute.name === 'MostReadPage'}">Najčitanije</router-link>
            </li>

            <div class="dropdown">
              <button class="btn btn-secondary dropdown-toggle" type="button" id="destinationsDropdownMenuButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                Destinations
              </button>
              <div class="dropdown-menu" aria-labelledby="destinationsDropdownMenuButton">
                <a v-for="destination in destinations" :key="destination.id" class="dropdown-item" :href="`/#/destination/${destination.id}`">{{ destination.name }}</a>
              </div>
            </div>
          </ul>
          <form v-if="canLogout" class="d-flex" @submit.prevent="logout">
            <button class="btn btn-outline-secondary" type="submit">Logout</button>
          </form>
        </div>
      </div>
    </nav>
  </div>
</template>

<script>

export default {

  name: "NavigationBar",
  data() {
    return {
      destinations: [],
      activities:[]
    };
  },
  computed: {
    canLogout() {
      return this.$route.name !== 'LoginPage';
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('jwt');
      this.$router.push({name: 'LoginPage'});
    },
    fetchDestinations() {
      this.$axios.get('http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination')
          .then(response => {
            this.destinations = response.data;

          })
          .catch(error => {
            console.error('Error fetching destinations:', error);
          });
    },
  },
  mounted() {
    this.fetchDestinations();

  }

}
</script>

<style scoped>
  .btn{
    left: auto;
    margin: 10px;
  }
  .nav-item{
    padding: 10px;
  }
</style>
