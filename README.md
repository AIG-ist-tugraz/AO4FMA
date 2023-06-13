# AO4FMA
Source code to accompany the paper "Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders"

# build image
# docker build -t ao4fma .

# create a folder for the experiment results and copy results from the Docker image to the folder:
# mkdir results
# docker run --rm --entrypoint tar ao4fma cC ./results . | tar xvC ./results