package app;

public class NavigationBar {
    public String main() {
        String html = "";
        html = html + """
            <div class="topnav">
                <div class='logo'> <a href='/'> <img src='logo.png' class='top-image' alt='RMIT logo' height='75'> </a> </div>
                <div class='topnav-content'>
                    <div class='dropdown'> 
                    <button class='dropbtn'>By Country</button>
                        <div class='dropdown-content'>
                            <a href='/Food-Loss-and-Waste-by-Country'>Food Loss and Waste</a>
                            <a href='/Location-Loss-and-Waste-by-Similarity'>Loss and Waste by Similarity</a> 
                        </div>
                    </div>
                    <div class='dropdown'> 
                    <button class='dropbtn'>By Food</button>
                        <div class='dropdown-content'>
                            <a href='/foodLossWasteChangeByFoodGroup'>Food Loss and Waste Change</a>
                            <a href='/similarFoodGroups'>Similar Food Groups</a>
                        </div>
                    </div>
                    <a href='mission'>Our Mission</a>
                    <!-- <a href='/'>Homepage</a> -->
                </div>
            </div>
        """;
        return html;
    }
}
