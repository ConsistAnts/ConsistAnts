## Alergia Stability Tests

The test resources in this folder are generated. We use them as input trace data sets for alergia to have reference alergia results so that we can notice changes. 

You can use the script `input/generate-inputs.py` to generate new input trace data sets. 

```
USAGE python generate-traces.py NUM_TRACES [MAX_TRACE_LEN [MIN_TRACE_LEN]]
```

The parameters for the different data set types were as follows:

```
> python generate-inputs.py 15 10 4 > small-dataset-1.trc
> python generate-inputs.py 15 20 4 > small-dataset-2.trc
> python generate-inputs.py 30 10 4 > medium-dataset-1.trc
> python generate-inputs.py 30 20 4 > medium-dataset-2.trc
> python generate-inputs.py 60 10 4 > large-dataset-1.trc
> python generate-inputs.py 60 20 4 > large-dataset-2.trc
```

