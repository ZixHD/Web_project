
<template>
  <div>
  <h2>{{activityName}}</h2>
  <ul class="articles-list">
    <li v-for="article in articles" :key="article.id" class="article-item">
      <div class="article-box">
        <h3>{{ article.title }}</h3>
        <h3>{{ article.name }}</h3>
        <p>Destination: {{ article.destinationName }}</p>
        <p>{{ truncateText(article.text, 100)}}</p>
        <p>Date: {{ formatDate(article.date) }}</p>
        <button @click="showDetails(article.id)">Detaljnije</button>
      </div>
    </li>
  </ul>
    <div class="pagination">
      <button @click="changePage(page)" v-for="page in totalPages" :key="page" :class="{ active: page === currentPage }">
        {{ page }}
      </button>
    </div>
  </div>


</template>

<script>
export default {
  name: 'ActivityPage',
  data() {
    return {
      activityId: this.$route.params.id,
      activityName: '',
      articles: [],
      currentPage: 1,
      totalPages: 0,
      pageSize: 3,
    };
  },
  watch: {
    '$route.params.id': function (newId) {
      this.activityId = newId;
      this.fetchActivityName(newId)
      this.fetchDestinationDetails(this.activityId)
    }
  },
  methods: {
    fetchActivityName(activityId){
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/activity/find/${activityId}`)
          .then(response=> {
            this.activityName = response.data.text;

            // OVO SLUZI DA DOBIJANJE IMENA ACTIVITIJA
            this.getTotalPages(activityId)
          })
    },
    getTotalPages(articleId){
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/pages/${articleId}`)
          .then(response => {
            this.totalPages = response.data.length;
            this.totalPages = Math.ceil(this.totalPages / this.pageSize);
            this.fetchDestinationDetails(articleId)
          })
          .catch(error => {
            console.error('Error fetching destination details:', error);
          });
    },
    fetchDestinationDetails(activityId) {
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/activity/${activityId}?page=${this.currentPage}&pageSize=${this.pageSize}`)
          .then(response => {
            this.articles = response.data;


          })
          .catch(error => {
            console.error('Error fetching destination details:', error);
          });
    },
    parseDate(dateStr) {
      const [day, month, year] = dateStr.split('.');
      return new Date(year, month - 1, day);
    },
    formatDate(dateStr) {
      const date = this.parseDate(dateStr);
      return date.toLocaleDateString();
    },
    showDetails(id) {
      this.$router.push({ name: 'Article', params: {id} });
    },
    changePage(page) {
      if (page !== this.currentPage) {
        this.currentPage = page;
        console.log(this.currentPage);
        this.fetchDestinationDetails(this.activityId);
      }
    },
    truncateText(text, maxLength) {
      return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
    },
  },
  mounted() {
    this.fetchActivityName(this.activityId);
  },
}

</script>

<style scoped>
.articles-list {
  display: flex;
  flex-direction: column;
  gap: 20px;
  padding: 0;
  list-style: none;
}

.article-item {
  display: flex;
  justify-content: center;
}

.article-box {
  border: 1px solid #ccc;
  border-radius: 5px;
  padding: 20px;
  width: 100%;
  max-width: 600px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  background-color: #fff;
}

.article-box h3 {
  margin-top: 0;
}

.article-box p {
  margin: 10px 0;
}

.pagination {
  display: flex;
  justify-content: center;
  margin-top: 20px;
}

.pagination button {
  margin: 0 5px;
  padding: 5px 10px;
  border: none;
  border-radius: 3px;
  background-color: #007bff;
  color: white;
  cursor: pointer;
}

.pagination button.active {
  background-color: #0056b3;
}
</style>