
<template>
  <div>
    <div class="article-box">
      <h3>{{ articleName }}</h3>
      <p>{{ articleText }}</p>
      <p>Date: {{ formatDate(articleDate) }}</p>
    </div>

    <div class="tags-container">
      <h4>Activities:</h4>
      <div class="tags">
        <a
            v-for="activity in activities"
            :key="activity.id"
            :href="`#/activity/${activity.id}`"
            class="tag"
        >
          {{ activity.text}}
        </a>

      </div>
    </div>

    <div id="commentForm-container" class="commentForm-container">
      <form id="newCommentForm" method="POST" @submit.prevent="addComment">
        <h2>New comment</h2>
        <label for="commentName">Name</label>
        <textarea id="commentName" name="Name" v-model="newComment.name"></textarea>

        <label for="commentContent">Comment</label>
        <textarea id="commentContent" name="Comment" v-model="newComment.text"></textarea>

        <button id="commentButton" >Comment</button>

      </form>
    </div>

    <div id="commentsContainer" class="comments-container">
      <div id="comments">
        <h2>Comments</h2>
        <div class="comment">
          <li v-for="comment in comments" :key="comment.id" class="comment-item">
            <div class="comment-content">
              <span class="comment-name">{{comment.name}}</span>
              <span class="comment-text">{{comment.text}}</span>
            </div>
            <span class="comment-date">{{comment.date}}</span>
          </li>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {jwtDecode} from "jwt-decode";
export default {
  name: 'ArticlePage',
  data() {
    return {
      articleId: this.$route.params.id,
      articleText: '',
      articleName: '',
      articleDate: '',
      activities: [],
      comments: [],
      newComment: {
        name: '',
        text: ''
      }

    };
  },
  watch: {
    '$route.params.id': function (newId) {
      this.articleId = newId;
      this.fetchArticle(newId)
      this.fetchActivities(newId)
      this.fetchComments(newId)
    }
  },
  methods: {
    fetchActivities(articleId){
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/${articleId}/activities`)
          .then(response =>{
            const activityIds = response.data;
            activityIds.forEach(activityId => {
              this.findActivity(activityId);
            });

          })
          .catch(error => {
            console.error('Error fetching articles:', error);
          });
    },
    findActivity(activityId){
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/activity/find/${activityId}`)
          .then(response => {
            this.activities.push(response.data);
          });
    },
    fetchArticle(articleId) {
      this.$axios.get(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/id/` + articleId)
          .then(response => {
            this.articleName = response.data.name;
            this.articleText = response.data.text;
            this.articleDate = response.data.date;
          })
          .catch(error => {
            console.error('Error fetching articles:', error);
          });
    },
    fetchComments(articleId){
      this.$axios.get('http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/comments/' + articleId)
          .then(response => {
            this.comments = response.data.sort((a, b) => this.parseDate(b.date) - this.parseDate(a.date))



      })
          .catch(error => {
            console.error('Error fetching articles:', error);
          });
    },
    addComment(){
      this.newComment.text = document.getElementById("commentContent").value;
      console.log(this.newComment.text)
      console.log(this.articleId)
      const currentDate = new Date();
      const formattedDate = `${String(currentDate.getDate()).padStart(2, '0')}.${String(currentDate.getMonth() + 1).padStart(2, '0')}.${currentDate.getFullYear()}`;

      this.$axios.post('http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/comments/add',{
        article_id: this.articleId,
        name: this.newComment.name,
        text: this.newComment.text,
        date: formattedDate
      })
          // eslint-disable-next-line
          .then(response => {
            this.newComment.name = '';
            this.newComment.text = '';
            alert("Comment added successfully")
            window.location.reload()
          })
          // eslint-disable-next-line
          .catch(error => {
            alert("Error adding comment...")
          });
    },
    decodeJWT(token){
      try {
        const decoded = jwtDecode(token);
        const name = decoded.username + " " + decoded.surname
        this.newComment.name = name
      } catch (error) {
        console.error('Error decoding JWT:', error);
      }
    },
    parseDate(dateStr) {
      const [day, month, year] = dateStr.split('.');
      return new Date(year, month - 1, day);
    },
    formatDate(dateStr) {
      const date = this.parseDate(dateStr);
      return date.toLocaleDateString();
    },
    incrementVisits(articleId) {
      this.$axios.put(`http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/${articleId}/increment`)
          // eslint-disable-next-line
          .then(response => {
            console.log('Visits incremented successfully');
          })
          .catch(error => {
            console.error('Error incrementing visits:', error);
          });
    },
  },
  mounted() {
    this.fetchArticle(this.articleId);
    this.fetchActivities(this.articleId);
    this.fetchComments(this.articleId);
    this.incrementVisits(this.articleId);

    const token = localStorage.getItem('jwt');
    if (token) {
      this.decodeJWT(token);
    }
  },
}

</script>

<style>

.comment-item {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 10px;
  border-bottom: 1px solid #ccc;
}

.comment-content {
  display: flex;
  flex-direction: column;
}
#commentForm-container {
  flex-direction: column;
  display: flex;
  background-color: #f9f9f9;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin-top: 20px;
}

.commentForm-container textarea {
  width: 100%;
  padding: 12px;
  margin-bottom: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
  resize: vertical;
  font-family: 'Arial', sans-serif;
  font-size: 14px;
}

.commentForm-container label {
  margin-bottom: 8px;
  font-weight: bold;
  font-size: 14px;
}

#commentButton {
  padding: 10px 20px;
  background-color: #4CAF50;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

#commentButton:hover {
  background-color: #45a049;
}

#commentsContainer {
  display: flex;
  flex-direction: column;
  gap: 10px;
  margin-top: 20px;
}

.comment {
  display: flex;
  flex-direction: column;
  padding: 15px;
  border: 1px solid #ddd;
  border-radius: 8px;
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.comment h1 {
  font-size: 18px;
  margin-bottom: 10px;
  color: #333;
}



.comment-name {
  font-weight: bold;
  color: #333;
}

.comment-content {
  color: #666;
}

</style>