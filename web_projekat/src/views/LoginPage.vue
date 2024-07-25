<template>
  <div class="pt-3">
    <h1>Hello, please enter your credentials to continue using this site</h1>
    <form @submit.prevent="login">
      <div class="form-group">
        <label for="email">Email</label>
        <input v-model="email" type="email" class="form-control" id="email" aria-describedby="emailHelp" placeholder="Enter email">
        <small id="emailHelp" class="form-text text-muted">We'll never share your email with anyone else.</small>
      </div>
      <div class="form-group">
        <label for="exampleInputPassword1">Password</label>
        <input v-model="password" type="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
      </div>
      <button type="submit" class="btn btn-primary mt-3">Submit</button>
    </form>
  </div>
</template>

<script>
export default {
  name: "LoginPage",
  data() {
    return {
      email: '',
      password: '',
    }
  },
  methods: {
    login() {
      this.$axios.post('http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/users/login', {
        email: this.email,
        password: this.password,
      }).then(response => {

        const jwt = response.data.jwt
        localStorage.setItem('jwt', jwt)
        this.$router.push({name: 'HomePage'});
      })
    }
  },
}
</script>

<style scoped>

</style>
