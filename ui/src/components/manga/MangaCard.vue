<template>
    <div class="manga-card">
        <img :src="'http://localhost:8081/images/' + image" alt="cover" class="manga-card__poster"/>
        <div class="manga-card__name"> {{ name }}</div>
    </div>
</template>

<script lang="ts">
    import {Component, Vue, Prop} from 'vue-property-decorator';

    @Component
    export default class MangaCard extends Vue {
        @Prop({required: true})
        name: string

        @Prop({required: true})
        genre: string

        @Prop({required: false})
        image: string
    }
</script>

<style scoped>
    .manga-card {
        width: 200px;
        height: 300px;
        margin: 5px;
        padding: 5px;
    }

    .manga-card__name {
        text-align: left;
        font-weight: bold;
    }

    .manga-card__poster {
        height: 250px;
        max-width: 200px;
    }
</style>
