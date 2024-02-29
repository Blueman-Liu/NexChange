<!--<script setup>-->
<script>
import HelloWorld from './components/HelloWorld.vue'
import TheWelcome from './components/TheWelcome.vue'
import Login from "@/components/Login.vue";
import Index from "@/components/Index.vue";

export default {
  name: 'App',
  components: {
    Login,
    Index
  }
}
</script>

<template>
<!--  <header>-->
<!--    <img alt="Vue logo" class="logo" src="./assets/logo.svg" width="125" height="125" />-->

<!--    <div class="wrapper">-->
<!--      <HelloWorld msg="You did it!" />-->
<!--    </div>-->
    <div id="app">
      <Login />
    </div>
<!--  </header>-->

<!--  <main>-->
<!--    <TheWelcome />-->
<!--  </main>-->
</template>

<style scoped>
header {
  line-height: 1.5;
}

.logo {
  display: block;
  margin: 0 auto 2rem;
}

@media (min-width: 1024px) {
  header {
    display: flex;
    place-items: center;
    padding-right: calc(var(--section-gap) / 2);
  }

  .logo {
    margin: 0 2rem 0 0;
  }

  header .wrapper {
    display: flex;
    place-items: flex-start;
    flex-wrap: wrap;
  }
}

#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
