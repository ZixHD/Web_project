

let jwt=''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    document.getElementById("addForm").addEventListener('submit', function (e) {
        e.preventDefault();
        addDestination();
    });

});



function addDestination(){
    const name = $('#name').val();
    const description = $('#description').val();


    $.ajax({
        url: 'http://localhost:8081/Web_projekat_Teodor_Jakovljevic_RN9622_war_exploded/api/destination/add',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            name: name,
            description: description
        }),
        headers: {
            'Authorization': 'Bearer ' + jwt
        },
        success: function (response) {
            alert('Destination added successfully!');
            $('#name').val('');
            $('#description').val('');
            window.location.href='./allDestinations.html'
        },
        error: function (xhr, status, error) {
            alert('Error adding destination');
            console.error('Error:', error);
        }
    });
}