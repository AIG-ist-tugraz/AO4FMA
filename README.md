# AO4FMA

Source code to accompany the paper "Analysis Operations On The Run: Feature Model Analysis in Constraint-based Recommender Systems" will be presented in [SPLC'23](https://2023.splc.net).

## Table of Contents

- [Repository structure](#repository-structure)
- [How to reproduce the experiment](#how-to-reproduce-the-experiment)

## Repository structure

| *folder*                | *description*                                                          |
|-------------------------|------------------------------------------------------------------------|
| ./data/app.cfg          | the configuration file                                                 |
| ./data/camera.xml       | testing feature model                                                  |
| ./data/filter.mzn       | filter constraints using MiniZinc syntax                               |
| ./data/products.csv     | product assortment                                                     |
| ./data/transactions.csv | synthesized transactions                                               |
| ./data/query            | stores three user requirements used in calculating the Restrictiveness |
| ./lib                   | a library                                                              |
| ./src                   | source code                                                            |
| Dockerfile              | Dockerfile to build the Docker image                                   |
| results.txt             | results                                                                |

## How to reproduce the experiment

Build a Docker image of the **AO4FMA** with the following command:

```shell
docker build -t ao4fma .
```

> It took around 4-5 minutes to complete this step on an Apple M1 laptop.

Next, create a folder for the experiment results and copy results inside the Docker image to the folder:

```shell
mkdir results
docker run --rm --entrypoint tar ao4fma cC ./results . | tar xvC ./results
```
