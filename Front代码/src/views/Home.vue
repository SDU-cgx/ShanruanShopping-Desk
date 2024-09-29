<template>
    <div class="home">
        <Navigation />
        <router-view />
    </div>
</template>

<script>
import Navigation from '../components/Navigation'
export default {
    name: 'home',
    components: {
        Navigation
    }
}
</script>

<style scoped>
.home {
    margin: 0 auto;
    text-align: center;
    min-height: 100vh;
    background-color: #E4F9F5;
    color: #360909;
    overflow: hidden;
}
</style>