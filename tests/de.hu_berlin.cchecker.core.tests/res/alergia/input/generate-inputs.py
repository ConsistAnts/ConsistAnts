import random
import sys
import json

alphabet = [0,1,2,3,4]

args = sys.argv

if len(args) == 1:
	print("USAGE python generate-traces.py NUM_TRACES [MAX_TRACE_LEN [MIN_TRACE_LEN]]")
	sys.exit(0)

MAX_TRACE_LEN = 10
MIN_TRACE_LEN = 1
TRACE_NUM = 40

if len(args) >= 2:
	TRACE_NUM = int(args[1])
if len(args) >= 3:
	MAX_TRACE_LEN = int(args[2])
if len(args) >= 4:
	MIN_TRACE_LEN = int(args[3])

def random_trace():
	l = random.randrange(MIN_TRACE_LEN, MAX_TRACE_LEN)
	t = []
	for i in range(0, l):
		t.append(alphabet[random.randrange(0,len(alphabet))])
	t = map(lambda n: str(n), t)
	return t;


traces = []

# Generate traces
for i in range(0, TRACE_NUM):
	traces.append(random_trace())

root = {}

idToLabel = {}

for letter in alphabet:
	idToLabel[str(letter)] = letter
root["transitionIdToLabel"] = idToLabel

#"id":1,
#      "transitions": [
#        {
#          "id":1,
#          "ordinal":1
#        },
#         {
#          "id":3,
#          "ordinal":2
#        },

json_traces = []
id_counter = 1

for t in traces:
	json_trace = {}

	json_trace["id"] = id_counter
	id_counter += 1

	transition_counter = 1
	transitions = []

	for symbol in t:
		transitions.append({"id": symbol, "ordinal": transition_counter})	
		transition_counter += 1
	json_trace["transitions"] = transitions
	
	json_traces.append(json_trace)

root["traces"] = json_traces
print json.dumps(root, sort_keys=True, indent=4, separators=(',', ': '))


