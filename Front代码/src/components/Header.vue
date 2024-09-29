<template>
    <div class="header">
        <router-link to="/"></router-link>
        <div class="search">
            <HeaderSearch />
            <HeaderCategory />
        </div>
    </div>
</template>

<script>
import HeaderCategory from './HeaderCategory.vue'
import HeaderSearch from './HeaderSearch.vue'
export default {
    name: 'Header',
    components: {
        HeaderSearch,
        HeaderCategory
    },
}
</script>

<style scoped>
* {
    margin: 0;
    padding: 0;
}

.header {
    padding-left: 18%;
    margin: 35px auto;
    height: 58px;
    width: 100%;
    overflow: true;
}

.header .search {
    width: 500px;
    height: 100%;
    overflow: true;
    float: left;
}

.header>a {
    float: left;
    margin-right: 60px;
    width: 142px;
    height: 40px;
    text-decoration: none;
    background: url(../assets/logo.png) 100%;
    vertical-align: bottom;
}
</style>