server {
    listen 80;
    server_name your-domain.com;

    # Serve the 'latest' version as the default if no version is specified
    location / {
        root /var/www/html/angular-app/latest;
        try_files $uri $uri/ /index.html;
    }

    # Serve version 1 of the app
    location /v1/ {
        alias /var/www/html/angular-app/v1/;
        try_files $uri $uri/ /index.html;
    }

    # Serve version 2 of the app
    location /v2/ {
        alias /var/www/html/angular-app/v2/;
        try_files $uri $uri/ /index.html;
    }
}

sudo nginx -t  # Test the configuration for syntax errors
sudo systemctl reload nginx  # Reload NGINX

