## Blockwise Leftaligned

The test resources in this folder are generated. We use them as input trace data sets for the blockwise left-aligned learning algorithm to have reference results so that we can notice changes. 

You can use the script `generate-inputs.py` to generate new input trace data sets. 

```
USAGE python generate-traces.py NUM_TRACES [MAX_TRACE_LEN [MIN_TRACE_LEN]]
```

The parameters for the different data set types were as follows:

```
> python generate-inputs.py 15 10 4 > test-dataset-1.trc
> python generate-inputs.py 15 10 4 > test-dataset-2.trc
> python generate-inputs.py 15 10 4 > test-dataset-3.trc
> python generate-inputs.py 15 10 4 > test-dataset-4.trc
```

To convert the trace data sets into a format that our proof-of-concept implementation
can read, the `convert.py` script can be used.

e.g.
```
python convert.py test-dataset-4.trc > plain-test-dataset-4
``