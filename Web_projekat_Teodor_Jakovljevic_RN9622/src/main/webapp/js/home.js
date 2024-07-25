

let jwt=''
document.addEventListener('DOMContentLoaded', () => {
    jwt = localStorage.getItem('jwt')
    const temp = decodeJWT(jwt);
    console.log(temp.username, temp.surname)
    const user = temp.username + " " + temp.surname
    const userRole = temp.role;
    if (userRole !== 'admin') {
        document.getElementById('users-nav-item').style.display = 'none';
    }
    document.querySelector('h3.user.me-3#placeholder').textContent = user;
    document.getElementById("header").textContent = "Welcome, " + user;


});

function decodeJWT(token) {

    const parts = token.split('.');
    const payload = parts[1];
    const decodedPayload = atob(payload);
    const claims = JSON.parse(decodedPayload);

    return claims;
}
function logout(){
    localStorage.removeItem('jwt')
    window.location.href='../index.html'
}



