
let jwt=''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    const urlParams = new URLSearchParams(window.location.search);
    const id = urlParams.get('id');
    fetchDestination(id)
    document.getElementById("editForm").addEventListener('submit', function (e) {
        e.preventDefault();
        editDestination(id);
    });

});

function fetchDestination(id){
    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/' + id,
        type: 'GET',
        contentType: 'application/json',
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            $('#name').val(response.name);
            $('#description').val(response.description);

        },
        error: function (xhr, status, error) {
            alert('Error editing destination');
            console.error('Error:', error);
        }
    });
}
function editDestination(id){
    const name = $('#name').val();
    const description = $('#description').val();


    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/'+ id +'/edit',
        type: 'PUT',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            description: description
        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('Destination edited successfully!');
            $('#name').val('');
            $('#description').val('');
            window.location.href='./allDestinations.html'
        },
        error: function (xhr, status, error) {
            alert('Error editing destination');
            console.error('Error:', error);
        }
    });
}