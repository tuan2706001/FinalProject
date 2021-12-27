
const slider = new Splide( '.splide3', {
    type       : 'fade',
    rewind: true,
    autoplay: true,
    arrows: false,
    // arrow : 'splide__arrow arrow',
    // prev  : 'splide__arrow--prev prev',
    // next  : 'splide__arrow--next next',
});
slider.on( 'pagination:mounted', function ( data ) {
    // You can add your class to the UL element
    data.list.classList.add( 'pagination' );

} );
slider.mount();


