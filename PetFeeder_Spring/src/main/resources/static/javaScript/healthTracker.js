
    function showGraph(className) {
    // Hide all graphs
    document.querySelectorAll('.growth, .foodIntake, .scatter, .barChart, .histogram, .heatmap').forEach(section => {
        section.style.display = 'none';
    });

    // Display the selected graph
    document.querySelectorAll('.' + className).forEach(section => {
    section.style.display = 'block';
});

    // Remove active state from all buttons
    const buttons = document.querySelectorAll('.text-center .btn-outline-warning');
    buttons.forEach(button => {
    button.classList.remove('active');
});

    // Set the active state for the selected button
    const activeButton = document.querySelector(`.btn-outline-warning[onclick="showGraph('${className}')"]`);
    if (activeButton) {
    activeButton.classList.add('active');
}
}

    window.onload = () => {
    // Set the initial active state
    document.querySelectorAll('.carousel-item').item(0).classList.add("active");
    showGraph('growth');
};
