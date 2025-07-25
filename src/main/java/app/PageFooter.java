package app;

public class PageFooter {
    public String main() {
        String html = "<html>";
        html = html + """
            <div class='footer'>
                <a href='/'><img src='logo.png' class='top-image' alt='RMIT logo' height='75'>
                <div class='footerLinks'>
                    <a href='mission'><u>Our Mission</u></a>
                    <a href='/Food-Loss-and-Waste-by-Country'><u>Country Food Loss and Waste</u></a>
                    <a href='/Location-Loss-and-Waste-by-Similarity'><u>Country Loss and Waste Similarity</u></a> 
                    <a href='/foodLossWasteChangeByFoodGroup'><u>Food Loss and Waste Change</u></a>
                    <a href='/similarFoodGroups'><u>Similar Food Groups</u></a>
                </div>
                <div class='footerSocial'>
                    <p>Harrison, s4090946 Menuki, s4062268</p>
                    <p>be sure to include image referencing in a link here!</p>
                    <p>RMIT, 2024, All Rights Reserved</p>
                </div>
            </div>
        """;        
        return html;
    }
}