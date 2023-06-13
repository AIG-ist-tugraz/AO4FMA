# build image
# docker build -t ao4fma .

# create a folder for the experiment results and copy results from the Docker image to the folder:
# mkdir results
# docker run --rm --entrypoint tar ao4fma cC ./results . | tar xvC ./results