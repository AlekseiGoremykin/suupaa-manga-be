<template>
    <div>
        <h1>Manga catalog</h1>

        <div  class="container">
            <manga-card v-for="manga in info" v-bind:name="manga.name" v-bind:genre="manga.genre" v-bind:image="manga.coverImageId" :key="manga.id"/>
        </div>
    </div>
</template>

<script lang="ts">
    import MangaCard from '@/components/manga/MangaCard.vue';
    import Axios from 'axios';
    import {Component, Vue} from 'vue-property-decorator';

    @Component({
        components: {
            MangaCard
        }
    })
    export default class MangaContainer extends Vue {
        info: any = []

        mounted() {
            Axios.get('http://localhost:8081/mangas')
                .then(response => (this.info = response.data))
        }
    }
</script>

<style scoped>
    .container {
        margin: auto;
        width: 850px;
        display: flex;
        flex-wrap: wrap;
    }
</style>
