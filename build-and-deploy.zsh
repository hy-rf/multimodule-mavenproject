#!/bin/zsh

# TODO: add variable for server directory

# Step 1: Run Maven clean install
echo "Running Maven build..."
./mvnw clean install

# Check if Maven build was successful
if [[ $? -ne 0 ]]; then
  echo "Maven build failed. Exiting."
  exit 1
fi

# Step 2: Copy built JAR file to target directory
echo "Copying JAR to ../JAVASERVER..."
cp ./app/target/app-0.0.1.jar ../JAVASERVER

# Step 3: Change working directory to sbp-client-nuxt
echo "Switching to sbp-client-nuxt directory..."
cd ./sbp-client-nuxt || { echo "sbp-client-nuxt directory not found"; exit 1; }

# Step 4: Delete existing dist directory if it exists
echo "Cleaning up old dist directory (if exists)..."
rm -rf ./nuxt

# Step 5: Install dependencies
echo "Installing npm dependencies..."
npm i

# Step 6: Run Vue build
echo "Running Vue build..."
npm run build

# Check if npm build was successful
if [[ $? -ne 0 ]]; then
  echo "Vue build failed. Exiting."
  exit 1
fi

# Step 7: Run Nuxt server
echo "Running Nuxt server..."
npm run start

# Step 7: Copy built files to web server directory
# echo "Copying build output to /opt/homebrew/var/www/html/..."
# cp -R ./dist/* /opt/homebrew/var/www/html/

# Step 8: Start the Java server
echo "Starting Java server in background..."
cd ../JAVASERVER || { echo "JAVASERVER directory not found"; exit 1; }
nohup java -jar app-0.0.1.jar > output.log 2>&1 &

# Step 9: Start nginx server
echo "Starting nginx server..."
brew services start nginx

echo "All done!"
