if (window.console) {
  console.log("Welcome to your Ballotboard!");
}

$(".bal_name").click(function(){
  $('#bal_name').show();
})

$(".bal_desc").click(function(){
  $('#bal_desc').show();
})

// $(".star-normal").click(function(){
//   $(this).toggleClass("star-favorite", true );
//   $(this).toggleClass("star-normal", false );
// })

// $(".star-favorite").click(function(){
//   $(this).toggleClass("star-normal", true );
//   $(this).toggleClass("star-favorite", false );
// })
