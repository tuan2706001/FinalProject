const tinnoibat = new Splide( '.splide', {
    type       : 'loop',
    perPage    : 3,
    autoplay: true,
    gap: 50,
    arrows: false,
    breakpoints: {
        640: {
            perPage : 1,
        },
        1024:{
            perPage : 2,
        }
    },
});
tinnoibat.on( 'pagination:mounted', function ( data ) {
    // You can add your class to the UL element
    data.list.classList.add( 'splide__pagination--custom' );

} );
tinnoibat.mount();


