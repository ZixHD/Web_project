let jwt = ''
let user_id = ''
let article_id =''
document.addEventListener('DOMContentLoaded', (e) => {
    e.preventDefault()
    jwt = localStorage.getItem('jwt')
    const urlParams = new URLSearchParams(window.location.search);
    article_id = urlParams.get('id')
    const temp = decodeJWT(jwt);
    user = temp.email;
    user_id = temp.user_id

    fetchDestination()

    fetchData(article_id);

    document.getElementById("editForm").addEventListener('submit', function (e) {
        e.preventDefault();
        editArticle(article_id);
    });

    document.getElementById('destination').addEventListener('change', function() {
        const selectedDestinationId = this.value;
        fetchActivity(selectedDestinationId);
    });



});
function fetchDestination(){

    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination',
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            var select = $('#destination');
            select.empty();
            $.each(response, function(index, destination) {
                select.append($('<option>', {
                    value: destination.id,
                    text: destination.name,
                    description: destination.description
                }));
            });
            if (response.length > 0) {
                fetchActivity(response[0].id);
            }
        },
        error: function (xhr, status, error) {
            alert('Error adding destination');
            console.error('Error:', error);
        }
    });
}

function fetchActivity(id){

    const activitiesList = $('#activities');



    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/activity/' + id,
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function(response) {

            activitiesList.empty();

            response.forEach(function(activity) {
                activitiesList.append(`
                    <li>
                        <label>
                            <input type="checkbox" value="${activity.id}" class="activity-checkbox">
                            ${activity.text}
                        </label>
                    </li>
                `);
            });
            fetchArticleActivities(article_id)
        },
        error: function (xhr, status, error) {
            alert('Error adding destination');
            console.error('Error:', error);
        }
    });
}

function fetchArticleActivities(articleId) {
    console.log("123")
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/' + articleId + '/activities',
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function(response) {
            console.log("Fetched article activities:", response);
            const activityIds = response;
            $('.activity-checkbox').each(function() {
                if (activityIds.includes(parseInt(this.value))) {
                    console.log("Checking activity with ID:", this.value);
                    this.checked = true;
                }
            });
        },
        error: function (xhr, status, error) {
            alert('Error fetching article activities');
            console.error('Error:', error);
        }
    });
}

function fetchData(id){
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/id/' + id,
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (data) {
            console.log(data)
            $('#name').val(data.name);
            $('#text').val(data.text);
            $('#destination').val(data.destination_id);

        },
        error: function (xhr, status, error) {
            alert('Error editing destination');
            console.error('Error:', error);
        }
    });
}

function editArticle(id){
    const name = $('#name').val();
    const text = $('#text').val();
    const select = document.getElementById('destination');
    const destinationId = select.value;
    const date = getCurrentDateFormatted()
    const visits = 1;
    console.log("Name", name, "text", text, "Date", date, "Visits", visits, "User", user_id, "Destination", destinationId)
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/'+ id +'/edit',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            text: text,
            date: date,
            visits: visits,
            destination_id: destinationId,
            user_id: user_id
        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('Article edited successfully!');
            $('#name').val('');
            $('#text').val('');
            window.location.href='./allArticles.html'
        },
        error: function (xhr, status, error) {
            alert('Error editing article');
            console.error('Error:', error);
        }
    });
}

function getCurrentDateFormatted() {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const year = today.getFullYear();

    return `${day}.${month}.${year}.`;
}

function decodeJWT(token) {

    const parts = token.split('.');
    const payload = parts[1];
    const decodedPayload = atob(payload);
    const claims = JSON.parse(decodedPayload);

    return claims;
}

