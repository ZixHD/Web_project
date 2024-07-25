let jwt=''
let user=''
let destination=''
let user_id=''
let article_id = ''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    const temp = decodeJWT(jwt);
    user = temp.email;
    user_id = temp.user_id
    fetchData();
    getArticleSize();
    document.getElementById("addForm").addEventListener('submit', function (e) {
        e.preventDefault();
        addArticle();

    });

    document.getElementById('destination').addEventListener('change', function() {
        const selectedDestinationId = this.value;
        fetchActivity(selectedDestinationId);
    });


});

function decodeJWT(token) {

    const parts = token.split('.');
    const payload = parts[1];
    const decodedPayload = atob(payload);
    const claims = JSON.parse(decodedPayload);

    return claims;
}

function fetchData(){

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


function getCurrentDateFormatted() {
    const today = new Date();
    const day = String(today.getDate()).padStart(2, '0');
    const month = String(today.getMonth() + 1).padStart(2, '0');
    const year = today.getFullYear();

    return `${day}.${month}.${year}.`;
}
function getArticleSize(){
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article',
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (data) {
            article_id = data.length + 1
            console.log(article_id)
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
        },
        error: function (xhr, status, error) {
            alert('Error adding destination');
            console.error('Error:', error);
        }
    });
}
function addArticle(){
    const name = $('#name').val();
    const text = $('#text').val();
    const date = getCurrentDateFormatted();
    const visits = 1;
    const select = document.getElementById('destination');
    const destinationId = select.value;
    destination = destinationId;
    console.log("Name", name, "text", text, "Date", date, "Visits", visits, "User", user_id, "Destination", destinationId)



    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            text: text,
            date: date,
            visits: visits,
            destination_id: destination,
            user_id : user_id

        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            article_id = response.id;
            console.log(response.id)
            alert('Article added successfully!');
            $('#name').val('');
            $('#text').val('');
            joinTableAdd(article_id)

        },
        error: function (xhr, status, error) {
            alert('Error adding article');
            console.error('Error:', error);
        }
    });
}

function joinTableAdd(id){
    let checkedActivityIds = '';
    $('.activity-checkbox').each(function() {
        if ($(this).is(':checked')) {
            var activityId = $(this).val();
            console.log(activityId);
            if (checkedActivityIds !== '') {
                checkedActivityIds += ',';
            }
            checkedActivityIds += activityId;
        }
    });

    console.log('Checked Activity IDs:', checkedActivityIds.toString());
    let str = checkedActivityIds.toString();
    console.log("Str: " + str)
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/article/add/' + id + "/join",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            activity_id: checkedActivityIds.toString()
        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('Article added successfully!');
            $('#name').val('');
            $('#text').val('');
             window.location.href='./allArticles.html'
        },
        error: function (xhr, status, error) {
            alert('Error adding article');
            console.error('Error:', error);
        }
    });
}



