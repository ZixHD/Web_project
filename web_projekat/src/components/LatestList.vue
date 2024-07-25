<template>
  <div>
    <h2>Latest Articles</h2>
    <ul class="articles-list">
      <li v-for="article in articles" :key="article.id" class="article-item">
        <div class="article-box">
          <h3>{{ article.title }}</h3>
          <h3>{{ article.name }}</h3>
          <p>Destination: {{ article.destinationName }}</p>
          <p>{{ truncateText(article.text, 100)}}</p>
          <p>Date: {{ article.date }}</p>
          <button @click="showDetails(article)">Detaljnije</button>
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
  data() {
    return {
      articles: [],
      currentPage: 1,
      totalPages: 0,
      pageSize: 3,
    };
  },
  mounted() {
    this.fetchLatestArticles();
  },
  methods: {
    fetchPagination() {
      this.axios
          .get(`/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/pagination?page=${this.currentPage}&pageSize=${this.pageSize}`)
          .then((response) => {
            let sortedArticles = response.data.sort(
                (a, b) => this.parseDate(b.date) - this.parseDate(a.date)
            );

            let destinationPromises = sortedArticles.map((article) => {
              return this.fetchDestinationName(article.destination_id).then(
                  (destinationName) => {
                    console.log(article)
                    article.destinationName = destinationName;
                    return article;
                  }
              );
            });

            Promise.all(destinationPromises).then((updatedArticles) => {
              this.articles = updatedArticles;
            });
          })
          .catch((error) => {
            console.error("Error fetching latest articles:", error);
          });
    },
    fetchLatestArticles() {
      this.axios
          .get("/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article")
          .then((response) => {
            this.totalPages = Math.ceil(response.data.length / this.pageSize);
            this.fetchPagination();
          })
          .catch((error) => {
            console.error("Error fetching latest articles:", error);
          });
    },
    fetchDestinationName(destination_id) {
      return this.axios
          .get(`/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/${destination_id}`)
          .then((response) => {
            console.log(`Fetched destination name for id ${destination_id}:`, response.data.name);
            return response.data.name;
          })
          .catch((error) => {
            console.error(`Error fetching destination name for id ${destination_id}:`, error);
            return "Unknown";
          });
    },
    parseDate(dateStr) {
      const [day, month, year] = dateStr.split(".");
      return new Date(year, month - 1, day);
    },
    formatDate(dateStr) {
      const date = this.parseDate(dateStr);
      return date.toLocaleDateString();
    },
    showDetails(article) {
      this.$router.push({ name: "Article", params: { id: article.id } });
    },
    changePage(page) {
      if (page !== this.currentPage) {
        this.currentPage = page;
        this.fetchPagination();
      }
    },
    truncateText(text, maxLength) {
      return text.length > maxLength ? text.substring(0, maxLength) + '...' : text;
    },
  },
};
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
