# DVC Configuration
# Track datasets and large files with DVC

# .dvc/config (run: dvc init)
# core:
#   remote: myremote
#   autostage: true

# remote "myremote":
#   url: s3://mybucket/dvc-storage

# Example .dvc file for a dataset:
# data/iris.csv.dvc:
#   md5: abc123...
#   size: 4552
#   nfiles: 1

# Workflow:
# 1. dvc init
# 2. dvc remote add -d myremote s3://mybucket/dvc-storage
# 3. dvc add data/iris.csv
# 4. git add data/iris.csv.dvc .dvc/config
# 5. git commit -m "Track iris dataset with DVC"
# 6. dvc push

# In training script:
#   dvc.api.read("data/iris.csv")  -- reads tracked dataset
#   dvc repro                       -- reruns pipeline if deps changed
