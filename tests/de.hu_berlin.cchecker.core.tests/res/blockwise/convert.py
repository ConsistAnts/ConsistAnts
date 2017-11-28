#!/usr/bin/python
# This script reads trace data set files (json formatted) and converts them to the
# plain-text format used by the blockwise.py implementation.
import sys
import json

if len(sys.argv) <= 1:
    print("USAGE python convert.py INPUTFILE")

filename = sys.argv[1]

content = json.load(open(filename, "r"))

idToLabelMap = content["transitionIdToLabel"]
traces = content["traces"]

mappings_str = ""
for key in idToLabelMap.keys():
    mappings_str += str(idToLabelMap[key]) + "=" + str(key) + " "
print mappings_str

for trace in traces:
    s = []
    for t in trace["transitions"]:
        s += str(idToLabelMap[t["id"]])
    print " ".join(s)
